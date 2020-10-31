import com.opencsv.bean.CsvBindByName;

public class IPLMostRuns {
    @CsvBindByName(column = "POS")
    public int pos;
    @CsvBindByName(column = "PLAYER")
    public String player;
    @CsvBindByName(column = "Mat")
    public int mat;
    @CsvBindByName(column = "Inns")
    public int inns;
    @CsvBindByName(column = "Runs")
    public int runs;
    @CsvBindByName(column = "NO")
    public int no;
    @CsvBindByName(column = "HS")
    public int hs;
    @CsvBindByName(column = "Avg")
    public double avg;
    @CsvBindByName(column = "BF")
    public int  bf;
    @CsvBindByName(column = "SR")
    public double sr;
    @CsvBindByName(column = "100")
    public int hundred;
    @CsvBindByName(column = "50")
    public int fifty;
    @CsvBindByName(column = "4s")
    public int fours;
    @CsvBindByName(column = "6s")
    public int sixes;
    @Override
    public String toString() {
        return "IPLMostRuns{" +
                "Name='" + player + '\'' +
                ", runs=" + runs+
                '}';
    }
}
