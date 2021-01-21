package kitchenpos.order.domain;

import kitchenpos.table.domain.OrderTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_table_id")
    private OrderTable orderTable;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderedTime;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    protected Order() {
    }

    public Order(OrderTable orderTable, List<OrderLineItem> orderLineItems) {
        checkOrderTable(orderTable);
        this.orderStatus = OrderStatus.COOKING;
        this.orderedTime = LocalDateTime.now();
        this.orderTable = orderTable;
        this.orderLineItems = orderLineItems;
    }

    public Order(Long id, OrderTable orderTable, List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.orderStatus = OrderStatus.COOKING;
        this.orderedTime = LocalDateTime.now();
        this.orderTable = orderTable;
        this.orderLineItems = orderLineItems;
    }

    public Long getId() {
        return id;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        validationOrderStatus(orderStatus);
        this.orderStatus = orderStatus;
        this.orderedTime = LocalDateTime.now();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void validationOrderStatus(OrderStatus orderStatus) {
        if (Objects.equals(OrderStatus.COMPLETION, this.orderStatus)) {
            throw new IllegalArgumentException("주문이 완료된 상태입니다.");
        }

        if(Objects.isNull(orderStatus)) {
            throw new IllegalArgumentException("올바른 상태값을 입력해주시기 바랍니다.");
        }
    }

    private void checkOrderTable(OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException("테이블이 비어있습니다.");
        }
    }

    public void initialItems() {
        orderLineItems.forEach(orderLineItem -> orderLineItem.addOrder(this));
    }
}