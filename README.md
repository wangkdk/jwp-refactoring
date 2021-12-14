# 키친포스

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |

## 1단계 - 테스트를 통한 코드 보호

### 요구사항
- [x] kitchenpos 패키지의 코드를 보고 키친포스의 요구사항 작성
- [ ] 정리한 요구 사항을 토대로 테스트 코드 작성

--- 
### 상품

- 상품을 등록할 수 있다.
  - 상품의 가격이 올바르지 않으면 등록할 수 없다.
    - 상품의 가격은 0 원 이상이어야 한다.
- 상품의 목록을 조회할 수 있다.

### 메뉴 그룹

- 메뉴 그룹을 등록할 수 있다.
- 메뉴 그룹의 목록을 조회할 수 있다.

### 메뉴

- 메뉴를 등록할 수 있다.
  - 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
      - 메뉴의 가격은 0 원 이상이어야 한다.
      - 메뉴의 가격은 메뉴상품 금액 (가격 * 수량) 과 같거나 작아야 한다.
  - 메뉴 그룹이 존재하지 않으면 등록할 수 없다.
  - 상품이 존재하지 않으면 등록할 수 없다.
- 메뉴가 등록되면서 메뉴 상품을 함께 등록 된다.
- 메뉴 목록을 조회할 수 있다.

### 주문 테이블

- 주문 테이블을 등록할 수 있다.
- 주문 테이블 목록을 조회할 수 있다.
- 주문 테이블의 주문 등록 가능 여부를 변경할 수 있다.
  - 테이블 그룹에 속해있지 않아야 한다.
  - 주문 상태가 계산 완료 상태여야 한다.
- 방문한 손님 수를 갱신할 수 있다.
  - 손님 수는 최소 0명 이어야 한다.
  - 테이블이 빈 테이블이 아니어야 한다.

### 주문 테이블 그룹

- 주문 테이블 그룹을 등록할 수 있다.
  - 주문 테이블 그룹은 최소 2 테이블 이상이어야 한다.
  - 주문 테이블은 빈 테이블이 아니어야 한다.
  - 주문 테이블이 주문 테이블 그룹에 속해 있지 않아야 한다.
- 주문 테이블 그룹에서 주문 테이블을 삭제할 수 있다.
  - 그룹에 속한 테이블들의 주문 상태가 계산 완료 상태여야 한다.

### 주문

- 주문을 등록할 수 있다.
  - 주문 항목이 존재 해야 한다.
  - 주문 테이블이 존재 해야 한다.
  - 주문이 등록되면 주문 상태는 조리 상태가 된다.
- 주문이 등록되면서 주문 항목도 함께 등록 된다.
- 주문 목록을 조회할 수 있다.
- 주문 상태를 갱신할 수 있다.
  - 주문 상태가 계산완료 상태가 아니어야 한다.
--- 
