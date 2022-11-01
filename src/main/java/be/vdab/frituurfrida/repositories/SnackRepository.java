package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.dto.AantalVerkochteSnacksPerId;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SnackRepository {
    private final JdbcTemplate template;

    public SnackRepository(JdbcTemplate template) {
        this.template = template;
    }

    private final RowMapper<Snack> snackRowMapper =
            (result, rowNum) ->
                    new Snack(result.getLong("id"),
                            result.getString("naam"),
                            result.getBigDecimal("prijs"));

    public Optional<Snack> findById(long id) {
        try {
            var sql = """
                    select id, naam, prijs
                    from snacks where id = ?
                    """;
            return Optional.of(template.queryForObject(sql, snackRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public void update(Snack snack) {
        var sql = """
                update snacks
                set naam = ?, prijs = ?
                where id = ?
                """;
        if (template.update(sql,
                snack.getNaam(), snack.getPrijs(),
                snack.getId()) == 0) {
            throw new SnackNietGevondenException();
        }
    }

    public List<Snack> findByBeginNaam(String beginNaam) {
        var sql = """
                 select id, naam, prijs
                 from snacks
                 where naam like ?
                """;
        return template.query(sql, snackRowMapper, beginNaam + '%');
    }

    public List<AantalVerkochteSnacksPerId> findAantalVerkochteSnacksPerId() {
        var sql = """
                select id, naam, sum(aantal) as totaalAantal
                from snacks
                left outer join dagverkopen
                on snacks.id = dagverkopen.snackId
                group by id
                order by id
                """;
        RowMapper<AantalVerkochteSnacksPerId> mapper = (result, rowNum) ->
                new AantalVerkochteSnacksPerId(result.getLong("id"),
                        result.getString("naam"),
                        result.getInt("totaalAantal"));
        return template.query(sql, mapper);
    }
}
