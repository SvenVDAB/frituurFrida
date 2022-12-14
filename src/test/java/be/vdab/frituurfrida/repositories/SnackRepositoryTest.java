package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(SnackRepository.class)
@Sql({"/insertSnacks.sql", "/insertDagverkopen.sql"})
public class SnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    public static final String SNACKS = "snacks";
    public final SnackRepository repository;

    public SnackRepositoryTest(SnackRepository repository) {
        this.repository = repository;
    }

    private long idVanTestSnack() {
        return jdbcTemplate.queryForObject(
                "select id from snacks where naam = 'test'", Long.class);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestSnack()))
                .hasValueSatisfying(snack ->
                        assertThat(snack.getNaam())
                                .isEqualTo("test"));
    }

    @Test
    void findByOnbestaandeIdVindtGeenSnack() {
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    void update() {
        var id = idVanTestSnack();
        var snack = new Snack(id, "test", BigDecimal.TEN);
        repository.update(snack);
        assertThat(countRowsInTableWhere(SNACKS, "prijs=10 and id=" + id))
                .isOne();
    }

    @Test
    void updateOnbestaandeSnackGeeftEenFout() {
        assertThatExceptionOfType(SnackNietGevondenException.class).isThrownBy(
                () -> repository.update(new Snack(-1, "test", BigDecimal.TEN)));
    }

    @Test
    void findByBeginNaam() {
        assertThat(repository.findByBeginNaam("t"))
                .hasSize(countRowsInTableWhere(SNACKS, "naam like 't%'"))
                .extracting(Snack::getNaam)
                .allSatisfy(naam -> assertThat(naam.toLowerCase()).startsWith("t"))
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void aantalVerkochteSnacksPerId() {
        var aantalVerkochteSnacksPerId = repository.findAantalVerkochteSnacksPerId();
/*        assertThat(aantalVerkochteSnacksPerId)
                .hasSize(super.jdbcTemplate.queryForObject("select count(id) from snacks", Integer.class));*/
        assertThat(aantalVerkochteSnacksPerId)
                .hasSize(countRowsInTable(SNACKS));
        var rij1 = aantalVerkochteSnacksPerId.get(0);
        System.out.println("x = " + rij1);
        assertThat(rij1.totaalAantal())
                .isEqualTo(super.jdbcTemplate
                        .queryForObject("select sum(aantal) from dagVerkopen where snackId = " + rij1.id(),
                                Integer.class));
    }
}
