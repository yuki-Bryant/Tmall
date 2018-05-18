package com.taotao.portal.pojo;

/**
 * 购物车商品pojo
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/15
 * Time: 15:23
 */
public class CartItem {
    private Long id;
    private String title;
    private Long price;
    private String image;
    //购物车商品数量
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
