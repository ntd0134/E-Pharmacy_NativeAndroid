package prm.example.project.models.request_data;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public class DrugData implements Serializable {
    @SerializedName("pageNumber")
    private int pageNumber;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("totalElements")
    private int totalElements;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("elements")
    private List<drugItem> elements;

    public DrugData() {
    }

    public DrugData(int pageNumber, int pageSize, int totalElements, int totalPages, List<drugItem> elements) {
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

    public List<drugItem> getElements() {
        return elements;
    }

    public void setElements(List<drugItem> elements) {
        this.elements = elements;
    }

    @Data
    public class drugItem implements Serializable{
        @SerializedName("id")
        private String id;
        @SerializedName("description")
        private String description;
        @SerializedName("name")
        private String name;
        @SerializedName("cost")
        private float cost;
        @SerializedName("categoryName")
        private String categoryName;
        @SerializedName("supplierName")
        private String supplierName;
        @SerializedName("unitName")
        private String uniName;

        public drugItem() {
        }

        public drugItem(String id, String description, String name, float cost, String categoryName, String supplierName, String uniName) {
            this.id = id;
            this.description = description;
            this.name = name;
            this.cost = cost;
            this.categoryName = categoryName;
            this.supplierName = supplierName;
            this.uniName = uniName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getCost() {
            return cost;
        }

        public void setCost(float cost) {
            this.cost = cost;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getUniName() {
            return uniName;
        }

        public void setUniName(String uniName) {
            this.uniName = uniName;
        }

        @Override
        public String toString() {
            return "drugItem{" +
                    "id='" + id + '\'' +
                    ", description='" + description + '\'' +
                    ", name='" + name + '\'' +
                    ", cost=" + cost +
                    ", categoryName='" + categoryName + '\'' +
                    ", supplierName='" + supplierName + '\'' +
                    ", uniName='" + uniName + '\'' +
                    '}';
        }

    }
}
