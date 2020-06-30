public class Main {
    public static void main(String[] args) {
        // инициализация анализаторов для проверки в порядке данного набора анализаторов
        String[] spamKeywords = {"spam", "bad"};
        int commentMaxLength = 40;
        TextAnalyzer[] textAnalyzers1 = {
                new SpamAnalyzer(spamKeywords),
                new NegativeTextAnalyzer(),
                new TooLongTextAnalyzer(commentMaxLength)
        };
        TextAnalyzer[] textAnalyzers2 = {
                new SpamAnalyzer(spamKeywords),
                new TooLongTextAnalyzer(commentMaxLength),
                new NegativeTextAnalyzer()
        };

        public Label checkLabels (TextAnalyzer[]analyzers, String text){
            for (int i = 0; i < analyzers.length; i++)
                if (analyzers[i].processText(text) != Label.OK) {
                    return analyzers[i].processText(text);
                }
            return Label.OK;
        }
    }


    public interface TextAnalyzer {
        Label processText(String text);
    }

    public enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }

    public abstract class KeywordAnalyzer implements TextAnalyzer {
        protected abstract String[] getKeywords();

        protected abstract Label getLabel();

        public Label processText(String text) {
            String[] keywords = getKeywords();
            for (String keyword : keywords) {
                if (text.contains(keyword)) {
                    return getLabel();
                }
            }
            return Label.OK;
        }
    }

    public static class SpamAnalyzer extends KeywordAnalyzer {
        private String[] keywords;

        public SpamAnalyzer(String[] keywords) {
            this.keywords = keywords;
        }

        @Override
        protected String[] getKeywords() {
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.SPAM;
        }
    }

    public static class NegativeTextAnalyzer extends KeywordAnalyzer {
        private final String[] KEYWORDS = {":(", "=(", ":|"};

        @Override
        protected String[] getKeywords() {
            return KEYWORDS;
        }

        @Override
        protected Label getLabel() {
            return Label.NEGATIVE_TEXT;
        }
    }

    public static class TooLongTextAnalyzer implements TextAnalyzer {
        private int maxLength;

        public TooLongTextAnalyzer(int limit) {
            this.maxLength = limit;
        }

        @Override
        public Label processText(String text) {
            if (text.length() > maxLength) {
                return Label.TOO_LONG;
            } else {
                return Label.OK;
            }
        }
    }
}



