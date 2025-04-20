package itmo.blps.lab1.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Privilege {
    //общие
    PRODUCT_VIEW("PRODUCT_VIEW"),
    CATEGORY_VIEW("CATEGORY_VIEW"),

    //Пользователь
    ORDER_VIEW("ORDER_VIEW"),
    CART_VIEW("CART_VIEW"),
    CART_ADD_ITEM("CART_ADD_ITEM"),
    ORDER_CREATE("ORDER_CREATE"),
    DELIVERY_TRACK("DELIVERY_TRACK"),

    //Диспетчер
    START_DELIVERY("START_DELIVERY"),
    DELIVERY_UPDATE_STATUS("DELIVERY_UPDATE_STATUS"),
    WAITING_DELIVERIES("WAITING_DELIVERIES"),

    //АДМИн
    CREATE_PRODUCT("CREATE_PRODUCT"),
    CREATE_CATEGORY("CREATE_CATEGORY"),;

    public final String name;
}