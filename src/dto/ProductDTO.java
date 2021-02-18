package dto;

public class ProductDTO {
    private int productId;
    private String name;
    private String brand;
    private String description;
    private int groupId;
    private String inventoryable;
    private Double price;
    private byte[] imageBytes;

    public  ProductDTO(){
    }

    public ProductDTO(int productId, String name, String brand, String description, int groupId, String inventoryable, Double price, byte[] imageBytes) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.groupId = groupId;
        this.inventoryable = inventoryable;
        this.price = price;
        this.imageBytes = imageBytes;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getInventoryable() {
        return inventoryable;
    }

    public void setInventoryable(String inventoryable) {
        this.inventoryable = inventoryable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}

