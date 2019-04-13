package superservice.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Step;
import java.sql.SQLException;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import superservice.JooqClient;
import superservice.db.Tables;

public class Verifications {

  public static Result<Record> getAllRowsFromTableWhere(TableImpl table, Condition condition)
      throws SQLException {
    return DSL.using(JooqClient.getInstance().getConnection(), SQLDialect.SQLITE).
        select()
        .from(table)
        .where(condition)
        .fetch();
  }

  @Step
  public static void uploadsRowShouldHave(Integer id,
      String payloadMd5, String userId) throws SQLException {
    Result<Record> rows = Verifications.getAllRowsFromTableWhere(Tables.UPLOADS,
        (Tables.UPLOADS.ID.eq(id)));

    assertThat(rows)
        .as("Check there is only 1 row with id %s in %s table",
            id, Tables.UPLOADS.getName())
        .hasSize(1);

    Record record = rows.get(0);

    assertThat(record.getValue(Tables.UPLOADS.PAYLOAD_MD5.getName()).toString())
        .as("Check value of payload_md5 field")
        .isEqualTo(payloadMd5);
    assertThat(record.getValue(Tables.UPLOADS.USER_ID.getName()).toString())
        .as("Check value of user_id field")
        .isEqualTo(userId);
  }
}
