package lab;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;

/** Slice: entities, repositories, EntityManager, embedded DB, rolled-back transactions. NOT web or services. */
@DataJpaTest
class OrderRepositoryTest {

    @Autowired OrderRepository repo;
    @Autowired TestEntityManager em;
    @Autowired ApplicationContext ctx;

    @Test
    void derivedQueryOrdersByTotalDescending() {
        em.persist(new OrderEntity("ana", 500));
        em.persist(new OrderEntity("ana", 900));
        em.persist(new OrderEntity("bob", 100));
        em.flush();

        assertThat(repo.findByCustomerOrderByTotalCentsDesc("ana")).extracting(OrderEntity::getTotalCents)
                .containsExactly(900L, 500L);
    }

    @Test
    void jpqlAggregate() {
        em.persist(new OrderEntity("ana", 500));
        em.persist(new OrderEntity("ana", 900));
        assertThat(repo.totalSpent("ana")).isEqualTo(1400);
        assertThat(repo.totalSpent("nobody")).isZero();
    }

    @Test
    void showsWhatTheSliceLoads() {
        assertThat(ctx.containsBean("dataSource")).isTrue();
        assertThat(ctx.getBeanNamesForType(OrderRepository.class)).isNotEmpty();
        assertThat(ctx.getBeanNamesForType(AuditService.class)).as("@Service is not scanned").isEmpty();
        assertThat(ctx.containsBean("requestMappingHandlerMapping")).as("no web layer").isFalse();
    }
}
