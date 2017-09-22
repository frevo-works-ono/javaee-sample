package jp.co.frevoworks.javaee.csv.download.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import jp.co.frevoworks.javaee.csv.download.annotation.CsvColumn;
import lombok.Getter;

/**
 * レスポンスデータ
 */
public class ResultCsv<T> {

    /**
     * 出力フィールド
     */
    @Getter
    private String[] properties;
    /**
     * 出力ヘッダ
     */
    @Getter
    private String[] header;
    /**
     * ファイル名
     */
    @Getter
    private String csvFileName;
    /**
     * 出力データ
     */
    @Getter
    private List<T> data;

    public ResultCsv(String[] properties, String csvFileName, Class<T> clazz) {
        List<String> headerList = new ArrayList<>();
        
        if(properties.length == 0){
            // フィールドが指定されていない場合は全て出力対象
            properties = Stream.of(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CsvColumn.class))
                    .map(field -> field.getName())
                    .toArray((size) -> new String[size]);
        }
        
        Stream.of(properties).forEach(property -> {
            try {

                Field field = clazz.getDeclaredField(property);
                CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);
                headerList.add(csvColumn.header());
            } catch (NoSuchFieldException | SecurityException ex) {

            }
        });
        this.properties = properties;
        this.header = headerList.toArray(new String[headerList.size()]);
        this.data = new ArrayList<>();
        this.csvFileName = csvFileName;
    }

    public void add(T t) {
        this.data.add(t);
    }
}
