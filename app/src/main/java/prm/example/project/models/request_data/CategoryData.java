package prm.example.project.models.request_data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryData {
    @SerializedName("pageNumber")
    private int pageNumber;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("totalElements")
    private int totalElements;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("elements")
    private ArrayList<CategoryItem> elements;

    public CategoryData() {
    }

    public CategoryData(int pageNumber, int pageSize, int totalElements, int totalPages, ArrayList<CategoryItem> elements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.elements = elements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<CategoryItem> getElements() {
        return elements;
    }

    public void setElements(ArrayList<CategoryItem> elements) {
        this.elements = elements;
    }

    public static class CategoryItem{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        public CategoryItem(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
