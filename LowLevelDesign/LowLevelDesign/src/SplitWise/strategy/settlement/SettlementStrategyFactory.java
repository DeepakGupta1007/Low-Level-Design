package SplitWise.strategy.settlement;


public class SettlementStrategyFactory {
    public enum Type { GREEDY, OPTIMAL }

    public SettlementStrategy get(Type type) {
        return switch (type) {
            case GREEDY -> new GreedySettlementStrategy();
            case OPTIMAL -> new OptimalSettlementStrategy();
        };
    }
}
