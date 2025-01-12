package org.fortunerise.history.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fortunerise.history.entities.Bet;
import org.fortunerise.history.entities.bets.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @JsonProperty("promotionId")
    private Integer promotionId;

    // Default constructor for JSON deserialization
    public BetDto() {}

    public BetDto(Bet bet) {
        this.betAmount = bet.getBetAmount();
        if(bet instanceof StraightBet) {
            this.type = BetType.STRAIGHT;
        }else if(bet instanceof SplitBet) {
            this.type = BetType.SPLIT;
        }else if(bet instanceof StreetBet) {
            this.type = BetType.STREET;
        }else if(bet instanceof CornerBet) {
            this.type = BetType.CORNER;
        }else if(bet instanceof SixLineBet) {
            this.type = BetType.SIX_LINE;
        }else if(bet instanceof ColorBet) {
            this.type = BetType.COLOR;
        }else if(bet instanceof ParityBet) {
            this.type = BetType.PARITY;
        }else if(bet instanceof ColumnBet) {
            this.type = BetType.COLUMN;
        }else if(bet instanceof DozenBet) {
            this.type = BetType.DOZEN;
        }else if(bet instanceof HighLowBet) {
            this.type = BetType.HIGH_LOW;
        }

        this.fields = bet.getFields();
    }

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

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public Integer getPromotionId() {
        return promotionId;
    }
}
