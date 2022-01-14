# photogram

### 연관관계 개념잡기

(One To Many) 1 : N (Many To One)
ex) 사용자 : 게시글
⭐ fk는 'N'이 가지고있음

(One To Many) N : N (Many To One)
ex) 관객 : 영화
⭐ N:N의 관계에선 중간테이블이 필요
ex) 예매 테이블

<구독하기>
user테이블과 user테이블
1 : N
N : 1
중간테이블 : subscribe테이블 (user테이블고 subscribe의 관계는 항상 1: N이므로
fk는 subscribe의 테이블이 가짐)


