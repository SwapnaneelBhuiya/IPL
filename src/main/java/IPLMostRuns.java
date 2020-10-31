import com.opencsv.bean.CsvBindByName;

public class IPLMostRuns {
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getMat() {
        return mat;
    }

    public void setMat(int mat) {
        this.mat = mat;
    }

    public int getInns() {
        return inns;
    }

    public void setInns(int inns) {
        this.inns = inns;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getHs() {
        return hs;
    }

    public void setHs(int hs) {
        this.hs = hs;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getBf() {
        return bf;
    }

    public void setBf(int bf) {
        this.bf = bf;
    }

    public double getSr() {
        return sr;
    }

    public void setSr(double sr) {
        this.sr = sr;
    }

    public int getHundred() {
        return hundred;
    }

    public void setHundred(int hundred) {
        this.hundred = hundred;
    }

    public int getFifty() {
        return fifty;
    }

    public void setFifty(int fifty) {
        this.fifty = fifty;
    }

    public int getFours() {
        return fours;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public int getSixes() {
        return sixes;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

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
