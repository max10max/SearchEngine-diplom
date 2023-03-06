public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    @Override
    public int compareTo(PageEntry o) {
        if (this.count > o.count) return 1;
        else if (this.count < o.count) return -1;
        else return 0;
    }


    @Override
    public String toString() {
        return pdfName + " - " + page + " - " + count + "\n";
    }
}
