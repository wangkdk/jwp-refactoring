package kitchenpos.menu.dto;

import java.util.Objects;

import kitchenpos.menu.domain.MenuGroup;

public class MenuGroupResponse {

    private Long id;
    private String name;

    public MenuGroupResponse() {
    }

    public MenuGroupResponse(MenuGroup menuGroup) {
        this(menuGroup.getId(), menuGroup.getName().getValue());
    }

    public MenuGroupResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MenuGroupResponse that = (MenuGroupResponse)o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
