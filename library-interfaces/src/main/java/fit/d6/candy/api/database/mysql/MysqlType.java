package fit.d6.candy.api.database.mysql;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Year;

public enum MysqlType {

    TINYINT(Integer.class),
    SMALLINT(Integer.class),
    MEDIUMINT(Integer.class),
    INT(Integer.class),
    BIGINT(Long.class),
    DECIMAL(Double.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BIT(Integer.class),

    DATE(Date.class),
    TIME(Time.class),
    DATETIME(java.util.Date.class),
    TIMESTAMP(Timestamp.class),
    YEAR(Year.class),

    CHAR,
    VARCHAR,
    BINARY,
    VARBINARY,
    BLOB(Blob.class),
    TEXT,
    ENUM,
    SET,

    GEOMETRY,
    POINT,
    LINESTRING,
    POLYGON,

    MULTIPOINT,
    MULTILINESTRING,
    MULTIPOLYGON,
    GEOMETRYCOLLECTION,

    JSON;

    private final Class<?> supportClass;

    MysqlType() {
        this(String.class);
    }

    MysqlType(Class<?> supportClass) {
        this.supportClass = supportClass;
    }

    public Class<?> getSupportClass() {
        return supportClass;
    }

}
