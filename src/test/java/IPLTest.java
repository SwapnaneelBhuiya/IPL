import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class IPLTest {
    private static final String IPL_MOST_RUNS = "./src/main/resources/IPL2019FactsheetMostRuns.csv";
    private static final String IPL_MOST_WICKETS="./src/main/resources/IPL2019FactsheetMostWkts.csv";
    @Test
    public void givenIPLMostRunsFindBestScore() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.topBattingAverage(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals("MS Dhoni", censusCSV[0].player);
    }
    @Test
    public void givenIPLDataFindHighestStrikeRate() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.topStrikeRate(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals("Ishant Sharma", censusCSV[0].player);
    }
    @Test
    public void givenIPLDataFindMax6sAnd4s() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.maxFoursAndSixes(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals("Andre Russell", censusCSV[0].player);
    }
}