package ru.ecom4u.web.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ecom4u.web.controllers.dto.PaginatorDTO;
import ru.ecom4u.web.controllers.reqvalues.CategoryOrder;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;
import ru.ecom4u.web.domain.db.services.SitePropertiesService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny on 27.04.14.
 */
@Controller
@RequestMapping(value = "search")
public class SearchController extends AbstractController
{

    public static final String INDEX_DIR = "_index_product";

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SitePropertiesService sitePropertiesService;

    @Value("#{properties['fs.dir_path']}")
    private String fileStorageDir;

    private Analyzer analyzer = new RussianAnalyzer(Version.LUCENE_47);
    private File indexDir = null;

    @PostConstruct
    private void init()
    {
        // индексация данных
        int lenght = productService.countProducts().intValue();
        List<Product> products = productService.getProducts(0, lenght);

        indexDir = new File(new File(fileStorageDir), INDEX_DIR);
        if (this.indexDir.exists() && this.indexDir.isDirectory())
        {
            try
            {
                FileUtils.cleanDirectory(this.indexDir);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            this.indexDir.mkdirs();
        }

        try
        {
            Directory index = FSDirectory.open(this.indexDir);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, this.analyzer);
            IndexWriter w = new IndexWriter(index, config);

            for (Product product : products)
            {
                Document doc = new Document();

                doc.add(new TextField("title", product.getName(), Field.Store.YES));
                doc.add(new TextField("desc", product.getDescription(), Field.Store.YES));
                doc.add(new IntField("id", product.getId(), Field.Store.YES));

                w.addDocument(doc);
            }
            w.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    @RequestMapping(method = RequestMethod.GET)
    public String results(@RequestParam(value = "query", required = true) String query,
                          @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "order", required = false) CategoryOrder order,
                          Model model) throws ParseException, IOException
    {
        if (null == page)
            page = 0;

        model.asMap().put("categoryName", sitePropertiesService.getValue("rus_product_categories"));
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        model.asMap().put("query", query);
        model.asMap().put("order", order);

        if (null != query && !query.isEmpty())
        {
            // поиск товаров
            QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[]{"title", "desc"}, analyzer);
            parser.setDefaultOperator(QueryParser.Operator.OR);

            Query liceneQuery = parser.parse(QueryParser.escape(query));

            Directory index = FSDirectory.open(this.indexDir);
            IndexSearcher searcher = new IndexSearcher(IndexReader.open(index));

            int count = productService.countProducts().intValue();

            if (0 < count)
            {
                TopScoreDocCollector collector = TopScoreDocCollector.create(count, true);
                searcher.search(liceneQuery, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;

                List<Integer> ids = new ArrayList<Integer>(hits.length);
                for (ScoreDoc scoreDoc : hits)
                {
                    Document doc = searcher.doc(scoreDoc.doc);
                    ids.add(Integer.parseInt(doc.get("id")));
                }

                int firstIdx = page * DefaultController.PRODUCTS_ON_PAGE;
                int lastIdx = firstIdx + DefaultController.PRODUCTS_ON_PAGE;
                lastIdx = ids.size() < lastIdx ? ids.size() : lastIdx;

                model.asMap().put("products", productService.getByCollectionId(ids.subList(firstIdx, lastIdx), DefaultController.PRODUCTS_ON_PAGE));
                model.asMap().put("productsCount", ids.size());
                model.asMap().put("paginator", new PaginatorDTO(page, ids.size(), DefaultController.PRODUCTS_ON_PAGE));
            }
            else
            {
                model.asMap().put("productsCount", 0);
                model.asMap().put("paginator", new PaginatorDTO(page, 0, DefaultController.PRODUCTS_ON_PAGE));
            }
        }
        else
        {
            model.asMap().put("productsCount", 0);
            model.asMap().put("paginator", new PaginatorDTO(page, 0, DefaultController.PRODUCTS_ON_PAGE));
        }

        return "search";
    }
}
