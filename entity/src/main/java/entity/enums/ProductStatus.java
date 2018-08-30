package entity.enums;

public enum ProductStatus {
    AUDITING("审核中"),
    IN_SELL("销售中"),
    LOCKED("暂停销售"),
        FINISHED("已结束");

    ProductStatus(String desc) {
        this.desc = desc;
    }

    private String desc;

}
