package org.fortunerise.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fortunerise.entities.Bet;
import org.fortunerise.entities.bets.*;

import java.math.BigDecimal;
import java.util.List;


public class BetDto {

    public enum BetType {
        @JsonProperty("STRAIGHT")
        STRAIGHT,
        @JsonProperty("SPLIT")
        SPLIT,
        @JsonProperty("STREET")
        STREET,
        @JsonProperty("CORNER")
        CORNER,
        @JsonProperty("SIX_LINE")
        SIX_LINE,
        @JsonProperty("COLOR")
        COLOR,
        @JsonProperty("PARITY")
        PARITY,
        @JsonProperty("COLUMN")
        COLUMN,
        @JsonProperty("DOZEN")
        DOZEN,
        @JsonProperty("HIGH_LOW")
        HIGH_LOW;
    }

    @JsonProperty("type")
    private BetType type;
    @JsonProperty("betAmount")
    private BigDecimal betAmount;
    @JsonProperty("fields")
    private List<Integer> fields;

    // Default constructor for JSON deserialization
    public BetDto() {}

    public Bet convertToBet(Integer roll) {
        return switch (type) {
            case STRAIGHT -> new StraightBet(betAmount, roll, fields.get(0));
            case SPLIT -> new SplitBet(betAmount, roll, fields.get(0), fields.get(1));
            case STREET -> new StreetBet(betAmount, roll, fields.get(0), fields.get(1), fields.get(2));
            case CORNER -> new CornerBet(betAmount, roll, fields.get(0), fields.get(1), fields.get(2), fields.get(3));
            case SIX_LINE ->
                    new SixLineBet(betAmount, roll, fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5));
            case COLOR -> {
                ColorBet.Color color = (fields.get(0) == 0) ? ColorBet.Color.RED : ColorBet.Color.BLACK;
                yield new ColorBet(betAmount, roll, color);
            }
            case PARITY -> {
                ParityBet.Parity parity = (fields.get(0) == 0) ? ParityBet.Parity.EVEN : ParityBet.Parity.ODD;
                yield new ParityBet(betAmount, roll, parity);
            }
            case COLUMN -> {
                ColumnBet.Columns columns = switch (fields.get(0)) {
                    case 0 -> ColumnBet.Columns.FIRST;
                    case 1 -> ColumnBet.Columns.SECOND;
                    case 2 -> ColumnBet.Columns.THIRD;
                    default -> throw new IllegalArgumentException("Invalid column type!");
                };
                yield new ColumnBet(betAmount, roll, columns);
            }
            case DOZEN -> {
                DozenBet.Dozen dozen = switch (fields.get(0)) {
                    case 0 -> DozenBet.Dozen.FIRST;
                    case 1 -> DozenBet.Dozen.SECOND;
                    case 2 -> DozenBet.Dozen.THIRD;
                    default -> throw new IllegalArgumentException("Invalid dozen type!");
                };
                yield new DozenBet(betAmount, roll, dozen);
            }
            case HIGH_LOW -> {
                HighLowBet.HighLow highLow = (fields.get(0) == 0) ? HighLowBet.HighLow.HIGH : HighLowBet.HighLow.LOW;
                yield new HighLowBet(betAmount, roll, highLow);
            }
        };
    }


}
