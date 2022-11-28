package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.GastenBoekEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GastenBoekRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    public GastenBoekRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template)
                .withTableName("gastenboek")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<GastenBoekEntry> gastenBoekRowMapper =
            (result, rowNum) ->
                    new GastenBoekEntry(result.getLong("id"),
                            result.getString("naam"),
                            result.getDate("datum").toLocalDate(),
                            result.getString("bericht"));

    public List<GastenBoekEntry> findAll() {
        var sql = """
                select id, naam, datum, bericht
                from gastenboek
                order by id desc
                """;
        return template.query(sql, gastenBoekRowMapper);
    }

    public long create(GastenBoekEntry gastenBoekEntry) {
        return insert.executeAndReturnKey(Map.of("naam", gastenBoekEntry.getNaam(),
                        "datum", gastenBoekEntry.getDatum(),
                        "bericht", gastenBoekEntry.getBericht()))
                .longValue();
    }

    public void verwijder(Long[] ids) {
        if (ids.length != 0) {
            var sql = """
                    delete from gastenboek where id in (
                    """
                    + "?,".repeat(ids.length - 1)
                    + "?)";

            template.update(sql, ids);
        }
    }
}
