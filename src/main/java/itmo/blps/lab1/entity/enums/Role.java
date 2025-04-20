package itmo.blps.lab1.entity.enums;

import java.util.Set;

public enum Role {
    USER(Set.of(Privilege.PRODUCT_VIEW, Privilege.CATEGORY_VIEW,
            Privilege.ORDER_CREATE, Privilege.CART_VIEW,
            Privilege.CART_ADD_ITEM, Privilege.DELIVERY_TRACK)),

    DISPATCHER(Set.of(Privilege.CATEGORY_VIEW, Privilege.PRODUCT_VIEW,
            Privilege.START_DELIVERY, Privilege.DELIVERY_UPDATE_STATUS,
            Privilege.WAITING_DELIVERIES)),

    ADMIN(Set.of(Privilege.CATEGORY_VIEW, Privilege.PRODUCT_VIEW,
            Privilege.CREATE_PRODUCT, Privilege.CREATE_CATEGORY)),;

    private final Set<Privilege> privileges;

    Role(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }
}
