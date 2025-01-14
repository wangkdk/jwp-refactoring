package kitchenpos.order.domain;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kitchenpos.common.domain.Empty;
import kitchenpos.common.exception.BadRequestException;
import kitchenpos.common.exception.ExceptionMessage;

@Entity
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_group_id", foreignKey = @ForeignKey(name = "fk_order_table_table_group"))
    private TableGroup tableGroup;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Embedded
    private Empty empty;

    @Embedded
    private Orders orders;

    protected OrderTable() {
    }

    private OrderTable(Long id, TableGroup tableGroup, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroup = tableGroup;
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = new Empty(empty);
        this.orders = new Orders();
    }

    public static OrderTable of(int numberOfGuests, boolean empty) {
        return new OrderTable(null, null, numberOfGuests, empty);
    }

    public static OrderTable of(long id, TableGroup tableGroup, int numberOfGuests, boolean empty) {
        return new OrderTable(id, tableGroup, numberOfGuests, empty);
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        if (Objects.isNull(tableGroup)) {
            return null;
        }
        return tableGroup.getId();
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public Empty isEmpty() {
        return empty;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void changeTableGroup(TableGroup tableGroup) {
        this.tableGroup = tableGroup;
        this.empty.changeEmpty(false);
    }

    public void deleteTableGroup() {
        tableGroup = null;
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        validateChangeableNumberOfGuests();
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public void changeEmpty(final boolean empty) {
        validateNotCompletionOrderStatus();
        validateChangeableEmpty();
        this.empty.changeEmpty(empty);
    }

    public boolean isPossibleIntoTableGroup() {
        return empty.getValue() && Objects.isNull(tableGroup);
    }

    private void validateChangeableNumberOfGuests() {
        if (empty.getValue()) {
            throw new BadRequestException(ExceptionMessage.CANNOT_CHANGE_STATUS);
        }
    }

    private void validateChangeableEmpty() {
        if (Objects.nonNull(tableGroup)) {
            throw new BadRequestException(ExceptionMessage.CANNOT_CHANGE_STATUS);
        }
    }

    public void validateNotCompletionOrderStatus() {
        orders.validateNotCompletionOrderStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderTable that = (OrderTable)o;
        return empty == that.empty && Objects.equals(id, that.id) && Objects.equals(numberOfGuests,
            that.numberOfGuests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfGuests, empty);
    }
}
