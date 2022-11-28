package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.GastenBoekEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GastenBoekRepository.class)
@Sql("/insertGastenboek.sql")
public class GastenBoekRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String GASTENBOEK = "gastenboek";
    public final GastenBoekRepository repository;

    public GastenBoekRepositoryTest(GastenBoekRepository repository) {
        this.repository = repository;
    }

    @Test
    void findAll() {
        assertThat(repository.findAll())
                .hasSize(countRowsInTable(GASTENBOEK));
/*                .extracting(GastenBoekEntry::getDatum)
                .isSortedAccordingTo(Comparator.reverseOrder());*/
    }

    @Test
    void create() {
        var id = repository.create(new GastenBoekEntry(0, "test3",
                LocalDate.of(1973, 1, 6), "test3bericht"));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(GASTENBOEK, "id = " + id)).isOne();
    }

    private long idVanTest1Gastenboek() {
        return jdbcTemplate.queryForObject(
                "select id from gastenboek where naam = 'test'", Long.class);
    }

    private long idVanTest2Gastenboek() {
        return jdbcTemplate.queryForObject(
                "select id from gastenboek where naam = 'test2'", Long.class);
    }

    @Test
    void verwijder() {
        var id1 = idVanTest1Gastenboek();
        var id2 = idVanTest2Gastenboek();
        assertThat(countRowsInTableWhere(GASTENBOEK,
                "id in (" + id1 + "," + id2 + ")"))
                .isZero();
    }
}
