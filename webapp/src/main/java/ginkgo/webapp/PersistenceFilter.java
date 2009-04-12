package ginkgo.webapp;

import ginkgo.webapp.persistence.PersistenceService;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        PersistenceService persistenceService = Environment.getInstance().getPersistenceService();
        try {
            LOG.debug("begin transaction");
            persistenceService.beginTransaction();
            chain.doFilter(request, response);
            LOG.debug("commit transaction");
            persistenceService.commitTransaction();
        } catch (Throwable t) {
            LOG.error("error while transaction handling. do a rollback", t);
            persistenceService.rollbackTransaction();
        } finally {
            persistenceService.close();
        }
    }

    public void init(FilterConfig arg0) throws ServletException {

    }
}
