import java.util.LinkedList;

/**
 * 有两个String数组，一个装着一些产品的名字，另一个装着一些关键字。任务是输出所有包含关键字的产品名字。
 * 不需要包含所有的关键字，包含至少一个即可, 大小写需要区分.
 *
 * 我们可以将所有的关键字存放如一个 Trie 中，然后在所有的产品名字中搜索这些关键字。
 * Trie 还挺常见的，好多朋友都遇到了
 *
 * 时间复杂度：M*L2 + N*L1*L2
 *            ^       ^
 *         (build) (search)
 * 假设有N个产品，M个关键字，最长产品名字的长度是L1，最长关键字的长度是L2
 *
 */


class ProductSuggestion {

    class TrieNode {

        public TrieNode[] children;
        public String wordEndsHere; // word that end at this TrieNode

        public TrieNode() {
            this.children = new TrieNode[128]; // length of 128 should be able to cover all the ASCII characters
            this.wordEndsHere = null;
        }
    }

    class Trie {

        public TrieNode root;

        // build the trie
        public Trie(String[] searchWords) {
            this.root = new TrieNode();

            for (String searchWord : searchWords) {

                TrieNode current = this.root;

                for (int i = 0; i < searchWord.length(); i++) {
                    int charIndex = (int) searchWord.charAt(i);
                    if (current.children[charIndex] != null) {
                        current = current.children[charIndex];
                    } else {
                        current.children[charIndex] = new TrieNode();
                        current = current.children[charIndex];
                    }
                }

                // At this time point, current is the last node of this searchWord
                current.wordEndsHere = searchWord;
            }
        }

        // check if a word contains any search word
        public boolean containsSearchWord(String word) {

            for (int startIndex = 0; startIndex < word.length(); startIndex++) {

                int index = startIndex;
                TrieNode current = this.root;

                // 关键字不一定会在产品名字的开头，所以需要从产品名字的每一个字符开始都搜索一遍
                while (current != null && index < word.length()) {

                    if (current.wordEndsHere != null) {
                        System.out.println("-- Found keyword '" + current.wordEndsHere + "' in '" + word + "'");
                        return true;
                    };

                    int charIndex = (int) word.charAt(index);

                    if (current.children[charIndex] == null) {
                        break;
                    } else {
                        current = current.children[charIndex];
                        index++;
                    }
                }
            }
            return false;
        }
    }

    public LinkedList<String> run(String[] products, String[] searchWords) {

        LinkedList<String> result = new LinkedList<>();
        Trie T = new Trie(searchWords);

        // search products
        for (String product : products) {
            if (T.containsSearchWord(product)) result.add(product);
        }

        return result;
    }

    public static void main(String[] args) {
        String[] searchWords = new String[]{"Harry", "Philosopher", "muggle"};
        String[] products = new String[]{
                "Harry Potter",
                "Harris Potter",
                "Harry Potter and Philosopher's Stone",
                "Harry Potter and Operation System",
                "Hermione Granger and Philosopher's Stone",
                "Hermione Granger and Operation System",
                "Huan Fu and Philosopher's Stone",
                "Huan Fu and Operation System",
                "COMP131 magic for muggles",
                "COMP101 Defence Against Cyber Attacks"
        };
        ProductSuggestion P = new ProductSuggestion();
        LinkedList<String> res = P.run(products, searchWords);
        System.out.println("Final result: ");
        System.out.println(res.toString());
    }
}

