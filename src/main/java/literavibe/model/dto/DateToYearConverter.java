package literavibe.model.dto;


import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Calendar;
import java.util.Date;

public class DateToYearConverter implements Converter<Date, Integer> {

    @Override
    public Integer convert(MappingContext<Date, Integer> context) {
        if (context.getSource() == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(context.getSource());

        return calendar.get(Calendar.YEAR);
    }
}