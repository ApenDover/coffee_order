package ts.andrey.dto;

import ts.andrey.common.dto.InOutOrderingDTO;

import java.text.SimpleDateFormat;

public class InOutOrderingDTOView extends InOutOrderingDTO {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String giveDessert() {
        if (super.getDessert().isEmpty()) {
            return null;
        }
        final var stringBuilder = new StringBuilder();
        stringBuilder.append(super.getDessert().get(0));
        for (int i = 1; i < super.getDessert().size(); i++) {
            stringBuilder.append(", ");
            stringBuilder.append(super.getDessert().get(i));
        }
        return stringBuilder.toString();
    }

    public String giveDateOrder() {
        if (super.getDateOrder() == null) {
            return null;
        }
        return FORMATTER.format(super.getDateOrder());
    }

    public String giveDateReady() {
        if (super.getDateReady() == null) {
            return null;
        }
        return FORMATTER.format(super.getDateReady());
    }

}
