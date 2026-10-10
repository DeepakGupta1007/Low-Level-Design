package SplitWise.strategy.split;

import java.util.Map;

public class SplitStrategyFactory {
    public enum Type { EQUAL, EXACT, PERCENTAGE }

    private final Map<Type, SplitStrategy> strategies = Map.of(
            Type.EQUAL, new EqualSplitStrategy(),
            Type.EXACT, new ExactSplitStrategy(),
            Type.PERCENTAGE, new PercentageSplitStrategy()
    );

    public SplitStrategy get(Type type) {
        return strategies.get(type);
    }
}
