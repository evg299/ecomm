package ru.ecom4u.web.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
import ru.ecom4u.web.controllers.reqvalues.CategoryOrder;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.services.ProductCategoryService;
import ru.ecom4u.web.domain.db.services.ProductService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Evgeny on 27.04.14.
 */
@Controller
@RequestMapping(value = "search")
public class SearchController extends AbstractController {

    public static final String INDEX_DIR = "_index_product";

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;

    @Value("#{properties['fs.dir_path']}")
    private String fileStorageDir;

    @PostConstruct
    private void init() {
        //TODO: индексация данных
        int lenght = productService.countProducts().intValue();
        List<Product> products = productService.getProducts(0, lenght);

        File indexDir = new File(new File(fileStorageDir), INDEX_DIR);
        if (indexDir.exists() && indexDir.isDirectory()) {
            try {
                FileUtils.cleanDirectory(indexDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            indexDir.mkdirs();
        }

        try {
            Directory index = FSDirectory.open(indexDir);

            Analyzer analyzer = new RussianAnalyzer(Version.LUCENE_47);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);

            IndexWriter w = new IndexWriter(index, config);

            for (Product product : products) {
                Document doc = new Document();

                doc.add(new TextField("title", product.getName(), Field.Store.YES));
                doc.add(new TextField("desc", product.getDescription(), Field.Store.YES));
                doc.add(new IntField("id", product.getId(), Field.Store.YES));

                w.addDocument(doc);
            }
            w.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(method = RequestMethod.GET)
    public String results(@RequestParam(value = "query", required = true) String query, @RequestParam(value = "order", required = false) CategoryOrder order, Model model) {
        globalModelService.populateModel(model);

        model.asMap().put("categoryName", "Категории товаров");
        model.asMap().put("subCategories", productCategoryService.getRootProductCategories());

        model.asMap().put("query", query);
        model.asMap().put("order", order);



        return "search";
    }
}
