package kitchenpos.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import kitchenpos.application.MenuService;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuRestController.class)
class MenuRestControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MenuService menuService;

    @Autowired
    ObjectMapper objectMapper;

    MenuProduct menuProduct;

    @BeforeEach
    void setUp() {
        menuProduct = new MenuProduct();
        menuProduct.setSeq(1L);
        menuProduct.setMenuId(1L);
        menuProduct.setProductId(1L);
        menuProduct.setQuantity(1);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("메뉴등록 api 테스트")
    @Test
    public void create() throws Exception {
        Menu menu = new Menu();
        menu.setName("패스트푸드");
        menu.setMenuGroupId(1L);
        menu.setPrice(BigDecimal.valueOf(10000));
        menu.setMenuProducts(Arrays.asList(menuProduct));

        String requestBody = objectMapper.writeValueAsString(menu);

        Menu responseMenu = new Menu();
        responseMenu.setId(1L);
        responseMenu.setName("패스트푸드");
        responseMenu.setMenuGroupId(1L);
        responseMenu.setPrice(BigDecimal.valueOf(10000));
        responseMenu.setMenuProducts(Arrays.asList(menuProduct));
        String responseBody = objectMapper.writeValueAsString(responseMenu);

        when(menuService.create(any())).thenReturn(responseMenu);
        mockMvc.perform(post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody))
        ;

    }

    @DisplayName("메뉴 목록 Api 테스트")
    @Test
    void list() throws Exception {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("패스트푸드");
        menu.setMenuGroupId(1L);
        menu.setPrice(BigDecimal.valueOf(10000));

        menu.setMenuProducts(Arrays.asList(menuProduct));

        List<Menu> menus = Arrays.asList(menu);

        String responseBody = objectMapper.writeValueAsString(menus);

        when(menuService.list()).thenReturn(menus);
        mockMvc.perform(get("/api/menus")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody))
        ;
    }
}