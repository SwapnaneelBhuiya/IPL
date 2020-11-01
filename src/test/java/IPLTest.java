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
    @Test
    public void givenIPLDataFindBatsmanWithBestSR() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayersWithTopSRandBoundary(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals("Andre Russell", censusCSV[0].player);
    }
    @Test
    public void givenIPLDataFindBatsmanWithHighestAverageAndSR() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopAverageAndSR(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals("MS Dhoni", censusCSV[0].player);
    }
    @Test
    public void givenIPLDataFindBatsmanWithMaximumRunsAndBestAverage() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopAverageAndRuns(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals("David Warner", censusCSV[0].player);
    }
    @Test
    public void givenIPLDataFindBowlerWithTopAverages() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopBowlingAverage(IPL_MOST_WICKETS);
        IPLWickets[] censusCSV=new Gson().fromJson(sorted, IPLWickets[].class);
        Assert.assertEquals("Anukul Roy", censusCSV[13].player);
    }
    @Test
    public void givenIPLDataFindBowlerWithTopSR() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopSR(IPL_MOST_WICKETS);
        IPLWickets[] censusCSV=new Gson().fromJson(sorted, IPLWickets[].class);
        Assert.assertEquals("Alzarri Joseph", censusCSV[13].player);
    }
    @Test
    public void givenIPLDataFindBowlerWithBestEconomy() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopEconomy(IPL_MOST_WICKETS);
        IPLWickets[] censusCSV=new Gson().fromJson(sorted, IPLWickets[].class);
        Assert.assertEquals("Shivam Dube", censusCSV[0].player);
    }
    @Test
    public void givenIPLDataFindBowlerWithBestSRWith5wAnd4w() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopSRWithWickets(IPL_MOST_WICKETS);
        IPLWickets[] censusCSV=new Gson().fromJson(sorted, IPLWickets[].class);
        Assert.assertEquals("Alzarri Joseph", censusCSV[13].player);
    }
    @Test
    public void givenIPLDataFindBolwerWithBestSRandAverage() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopSRWAndAverage(IPL_MOST_WICKETS);
        IPLWickets[] censusCSV=new Gson().fromJson(sorted, IPLWickets[].class);
        Assert.assertEquals("Anukul Roy", censusCSV[13].player);
    }
    @Test
    public void givenIPLDataFindBowlerWithMaxWickets() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithMaxWickets(IPL_MOST_WICKETS);
        IPLWickets[] censusCSV=new Gson().fromJson(sorted, IPLWickets[].class);
        Assert.assertEquals("Imran Tahir", censusCSV[censusCSV.length-1].player);
    }
    @Test
    public void givenIPLDataFindCricketerWithBestBowlingAndBattingAverage() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopBowlingAverage(IPL_MOST_WICKETS);
        IPLWickets[] wickets=new Gson().fromJson(sorted, IPLWickets[].class);
        String sortedBat=iplAnalyser.getBatsmanWithMaxAverage((IPL_MOST_RUNS));
        IPLMostRuns[] average=new Gson().fromJson(sortedBat, IPLMostRuns[].class);
        for(int i=0;i<average.length;i++)
        {
            if(average[i].player.equals(wickets[13+i].player))
            {
                Assert.assertEquals("Sherfane Rutherford", average[i].player);
                break;
            }
        }
    }
    @Test
    public void givenIPLDataFindBestAllRounders() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithMaxWickets(IPL_MOST_WICKETS);
        IPLWickets[] wickets=new Gson().fromJson(sorted, IPLWickets[].class);
        String sortedBat=iplAnalyser.getPlayerWithTopRuns((IPL_MOST_RUNS));
        IPLMostRuns[] runs=new Gson().fromJson(sortedBat, IPLMostRuns[].class);
        int c=0;
        for(int i=0;i<runs.length;i++)
        {
            for(int j=wickets.length-1;j>=0;j--)
                if(runs[i].player.equals(wickets[j].player))
                {
                    Assert.assertEquals("Andre Russell", runs[i].player);
                    c=1;
                    break;
                }
            if(c==1)
                break;
        }
    }
    @Test
    public void givenIPLDataFindBatsmanWithMaximumAverageAndHundreds() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.getPlayerWithTopHundreds((IPL_MOST_RUNS));
        IPLMostRuns[] runs=new Gson().fromJson(sorted, IPLMostRuns[].class);
        String sortedBat=iplAnalyser.getBatsmanWithMaxAverage((IPL_MOST_RUNS));
        IPLMostRuns[] average=new Gson().fromJson(sortedBat, IPLMostRuns[].class);
        for(int i=0;i<runs.length;i++)
        {
            if(runs[i].player.equals(average[i].player))
            {
                Assert.assertEquals("Jos Buttler", average[i].player);
                break;
            }
        }
    }

}