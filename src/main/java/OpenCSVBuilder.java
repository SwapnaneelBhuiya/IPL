import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder {

        public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws IPLAnalyserException {
            CsvToBean<E> csvToBean;
            try {
                CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                csvToBean = csvToBeanBuilder.build();
                //Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
                //Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
                return csvToBean.iterator();
            } catch (IllegalStateException e) {
                throw new IPLAnalyserException(e.getMessage(),
                        IPLAnalyserException.ExceptionType.UNABLE_TO_PARSE);
            } catch (RuntimeException e) {
                throw new IPLAnalyserException(e.getMessage(),
                        IPLAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }
        }
        @Override
        public List<E> getCSVFileList(Reader reader, Class csvClass) throws IPLAnalyserException {
            return this.getCSVBean(reader,csvClass).parse();

        }

        private CsvToBean<E> getCSVBean(Reader reader, Class<E> csvClass) throws IPLAnalyserException {
            try {
                CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                return csvToBeanBuilder.build();
            } catch (IllegalStateException e) {
                throw new IPLAnalyserException(e.getMessage(), IPLAnalyserException.ExceptionType.UNABLE_TO_PARSE);
            }
        }
}
