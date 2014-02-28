package ru.ecom4u.web.controllers.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 27.02.14.
 */
public class PaginatorDTO {

    public static class Item {
        private boolean current;
        private int page;

        public boolean isCurrent() {
            return current;
        }

        public void setCurrent(boolean current) {
            this.current = current;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    private List<Item> items = new ArrayList<Item>();

    public PaginatorDTO(int currentPage, int totalElements, int elementsOnPage) {
        int countPages = totalElements / elementsOnPage;
        if (0 != totalElements % elementsOnPage)
            countPages++;

        for (int i = 0; i < countPages; i++) {
            addItem(i, currentPage == i);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(int page, boolean current) {
        Item item = new Item();
        item.setCurrent(current);
        item.setPage(page);
        items.add(item);
    }

    public boolean isShowPaginator() {
        if (1 < items.size())
            return true;

        return false;
    }
}
