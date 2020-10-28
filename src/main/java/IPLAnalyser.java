import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class IPLAnalyser {
    List<IPLMostRuns> csvFileList;
    public IPLAnalyser() {
        this.csvFileList = new ArrayList<IPLMostRuns>();
    }
    public int loadIPLdata(String csvFilePath) throws IPLAnalyserException {
        CsvToBean<IPLMostRuns> csvToBean;
        try (Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLMostRuns> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,  IPLMostRuns.class);
            while (censusCSVIterator.hasNext()) {
                this.csvFileList.add((censusCSVIterator.next()));
            }
            return csvFileList.size();
        }
        catch(IllegalStateException e) {
            throw new IPLAnalyserException(e.getMessage(),
                    IPLAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }catch (IOException|RuntimeException e) {
            throw new IPLAnalyserException(e.getMessage(),
                    IPLAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public String topBattingAverage(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.avg);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }
    public String topStrikeRate(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.sr);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }
    public String maxSixes(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.sixes);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }
    public String maxFours(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.fours);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }
    private void sort(Comparator<IPLMostRuns> censusComparator) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                IPLMostRuns census1 = csvFileList.get(j);
                IPLMostRuns census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }
            }
        }
    }
}