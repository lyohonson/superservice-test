package superservice;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class ValuesGenerator {


  private static AtomicInteger counter = new AtomicInteger(0);


  private static ValuesGenerator instance = null;


  public static ValuesGenerator getInstance() {
    if (instance == null) {
      synchronized (ValuesGenerator.class) {
        if (instance == null) {
          instance = new ValuesGenerator();
        }
      }
    }
    return instance;
  }

  private static final Random rnd = new Random();

  public String getUUIDAsString() {
    return UUID.randomUUID().toString();
  }

  private static final String ALPHA_STRING =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";

  public static String getRandomString(int count) {
    StringBuilder builder = new StringBuilder();

    count -= 1;

    while (count-- != 0) {
      int character = (int) (Math.random() * ALPHA_STRING.length());
      builder.append(ALPHA_STRING.charAt(character));
    }

    builder.append(counter);
    return builder.toString();
  }

  public int nextRandomInt(Integer limit) {
    int rand = rnd.nextInt(limit);
    if (rand == 0) {
      rand += 1;
    }
    return rand;
  }

  public int nextRandomInt(Integer min, Integer max) {
    int rand = rnd.nextInt((max - min) + 1) + min;
    if (rand == 0) {
      rand += 1;
    }
    return rand;
  }


  public static String getCyrillicRandomString() {
    String symbols = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";
    StringBuilder fdsl = new StringBuilder();
    for (int i = 0; i <= 4; i++) {
      fdsl.append(new Random().ints(4, 0, symbols.length())
          .mapToObj(symbols::charAt)
          .map(Object::toString)
          .collect(Collectors.joining()))
          .append(" ");
    }
    return new String(fdsl.toString().getBytes(), StandardCharsets.UTF_8);
  }

  public String getRandomJson() {
    return "{\"" + getRandomString(4) + "\": {\"" + getRandomString(5) + "\": \"" + getRandomString(
        5) + "\"},"
        + " \"" + getRandomString(6) + "\": [\"" + getRandomString(3) + "\", \"" + getRandomString(
        7) + "\"], "
        + "\"paid\": \"boolean\"}";
  }


}