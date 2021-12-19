package kitchenpos.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import kitchenpos.order.domain.Order;

public class OrderResponse {

    private Long id;
    private Long orderTableId;
    private String orderStatus;
    private LocalDateTime orderedTime;

    private List<OrderLineItemResponse> orderLineItems;

    public OrderResponse() {
    }

    public OrderResponse(Order order) {
        this(order.getId(), order.getOrderTableId(), order.getOrderStatus(), order.getOrderedTime(),
            order.getOrderLineItems().stream().map(
                    OrderLineItemResponse::new)
                .collect(Collectors.toList()));
    }

    public OrderResponse(Long id, Long orderTableId, String orderStatus, LocalDateTime orderedTime,
        List<OrderLineItemResponse> orderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderResponse that = (OrderResponse)o;
        return Objects.equals(id, that.id) && Objects.equals(orderTableId, that.orderTableId)
            && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(orderedTime,
            that.orderedTime) && Objects.equals(orderLineItems, that.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderTableId, orderStatus, orderedTime, orderLineItems);
    }
}
