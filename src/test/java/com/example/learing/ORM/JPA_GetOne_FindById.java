package com.example.learing.ORM;

import org.junit.jupiter.api.Test;

public class JPA_GetOne_FindById {
    @Test
    void main_test() {
        // JpaRepository
        /**
         * JpaRepository 에 의거한 getOne 구현방식은 파라미터 ID에 대한 값이 유효하지 않으면 Throw.
         * ID를 이용해서 찾지 못할 경우에도 Throw jakarta.persistence.EntityNotFoundException.
         * EntityNotFoundException 해당 에러의 경우 어떤 경우에서간 DB에서 ID에 해당하는 값을 찾지 못하는 경우 발생.
         */

        // CrudRepository
        // SimpleJpaRepository
        /**
         * CrudRepository 의 기본 구현체 - SimpleJpaRepository
         * findById의 경우 ID가 null일 경우에는 throw IllegalArgumentException를 발생.
         * 그외 DB에서 ID에 해당하는 Entity(값)을 찾지 못하는 경우에는 Optional#empty()를 리턴해준다.
         *
         * getOne, findById의 가장 큰 차이점은 Entity를 찾기위해 DB에서 요청한 결과가 없으면 Throw를 발생 혹은
         * Optional#empty()를 리턴해주는 데에 있다.
         *
         * getOne은 현재 Deprecated되어있는 상태이며 getOne대신 getReferenceById 사용을 권장하고 있다.
         * getReferenceById의 경우 첫 요청시 Entity가 없을경우에 Throw jakarta.persistence.EntityNotFoundException 발생.
         * 그외 특이사항으로는 프록시 객체를 이용한 Lazy Loading(지연로딩)을 사용함.
         * 직접적으로 Entity를 사용하는 경우 외에는 프록시 객체가 리턴됨.
         * 이러한 특징 때문에 Entity를 직접적으로 변화를 주더라도 JPA 의 Persistence로 인한 자동 수정 감지 같은 기능이 동작하지 않음.
         * 그렇기 때문에 프록시 객체를 이용해서 값에 수정이 있다면, flush를 반드시 호출하여 수동적으로 반영을 해야한다.
         */

    }

}
