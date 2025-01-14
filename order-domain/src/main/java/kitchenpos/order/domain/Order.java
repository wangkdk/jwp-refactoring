package kitchenpos.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kitchenpos.common.exception.BadRequestException;
import kitchenpos.common.exception.ExceptionMessage;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_orders_order_table"))
    private OrderTable orderTable;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(updatable = false)
    private LocalDateTime orderedTime;

    @Embedded
    private OrderLineItems orderLineItems;

    protected Order() {
    }

    private Order(Long id, OrderTable orderTable, OrderStatus orderStatus, LocalDateTime orderedTime,
        OrderLineItems orderLineItems) {
        validate(orderTable);
        this.id = id;
        this.orderTable = orderTable;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
        orderTable.addOrder(this);
    }

    private void validate(OrderTable orderTable) {
        if (orderTable.isEmpty().getValue()) {
            throw new BadRequestException(ExceptionMessage.WRONG_VALUE);
        }
    }

    public static Order of(OrderTable orderTable, List<OrderLineItem> orderLineItems) {
        return new Order(null, orderTable, OrderStatus.COOKING,
            LocalDateTime.now(), new OrderLineItems(orderLineItems));
    }

    public Long getId() {
        return id;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public void changeOrderStatus(final OrderStatus orderStatus) {
        if (this.orderStatus.isCompletion()) {
            throw new BadRequestException(ExceptionMessage.CANNOT_CHANGE_STATUS);
        }
        this.orderStatus = orderStatus;
    }

    public List<Long> getMenuIds() {
        return orderLineItems.getValue().stream()
            .map(OrderLineItem::getMenuId)
            .collect(Collectors.toList());
    }

    public void validateNotCompletionOrderStatus() {
        if (!orderStatus.isCompletion()) {
            throw new BadRequestException(ExceptionMessage.CANNOT_CHANGE_STATUS);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order)o;
        return Objects.equals(id, order.id) && orderStatus == order.orderStatus && Objects.equals(
            orderedTime, order.orderedTime) && Objects.equals(orderLineItems, order.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderStatus, orderedTime, orderLineItems);
    }
}
