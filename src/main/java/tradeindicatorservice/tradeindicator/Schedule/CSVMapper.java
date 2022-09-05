package tradeindicatorservice.tradeindicator.Schedule;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import tradeindicatorservice.tradeindicator.Entity.MariaConclusion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVMapper {

    public void csvFileOut(Class<?> clazz, File csvFile, List<MariaConclusion> dataList) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper.enable(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS)
                    .schemaFor(clazz).withHeader().withColumnSeparator(',').withLineSeparator("\n");
            ObjectWriter writer = csvMapper.writer(csvSchema);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(csvFile), "MS949");
            writer.writeValues(outputStreamWriter).writeAll(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
