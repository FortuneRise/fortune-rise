package org.fortunerise.dtos;

import org.fortunerise.entities.Bet;
import org.fortunerise.entities.bets.*;

import java.math.BigDecimal;
import java.util.List;


public class BetDto {

    public enum BetType {
        STRAIGHT,
        SPLIT,
        STREET,
        CORNER,
        SIX_LINE,
        COLOR,
        PARITY,
        COLUMN,
        DOZEN,
        HIGH_LOW;
    }

    private BetType type;
    private BigDecimal betAmount;
    private List<Integer> fields;
    private String typeString;

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
                ColorBet.Color color = (typeString.equals("RED")) ? ColorBet.Color.RED : ColorBet.Color.BLACK;
                yield new ColorBet(betAmount, roll, color);
            }
            case PARITY -> {
                ParityBet.Parity parity = (typeString.equals("EVEN")) ? ParityBet.Parity.EVEN : ParityBet.Parity.ODD;
                yield new ParityBet(betAmount, roll, parity);
            }
            case COLUMN -> {
                ColumnBet.Columns columns = switch (typeString) {
                    case "FIRST" -> ColumnBet.Columns.FIRST;
                    case "SECOND" -> ColumnBet.Columns.SECOND;
                    case "THIRD" -> ColumnBet.Columns.THIRD;
                    default -> throw new IllegalArgumentException("Invalid typeString!");
                };
                yield new ColumnBet(betAmount, roll, columns);
            }
            case DOZEN -> {
                DozenBet.Dozen dozen = switch (typeString) {
                    case "FIRST" -> DozenBet.Dozen.FIRST;
                    case "SECOND" -> DozenBet.Dozen.SECOND;
                    case "THIRD" -> DozenBet.Dozen.THIRD;
                    default -> throw new IllegalArgumentException("Invalid typeString!");
                };
                yield new DozenBet(betAmount, roll, dozen);
            }
            case HIGH_LOW -> {
                HighLowBet.HighLow highLow = (typeString.equals("HIGH")) ? HighLowBet.HighLow.HIGH : HighLowBet.HighLow.LOW;
                yield new HighLowBet(betAmount, roll, highLow);
            }
        };
    }


}
