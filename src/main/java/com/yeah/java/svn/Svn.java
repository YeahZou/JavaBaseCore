package com.yeah.java.svn;

import static java.lang.System.out;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;

public class Svn {
	public static void main(String[] args)  throws SVNException {
		DefaultSVNOptions myOptions = new DefaultSVNOptions();
	    SVNClientManager clientManager = SVNClientManager.newInstance(myOptions, "admin", "123456");
	    SVNURL svnUrl = SVNURL.parseURIEncoded("svn://192.168.0.24/project/");
	    SVNRepository svnRepository = clientManager.createRepository(svnUrl, true);
	    SVNURL svnRoot = svnRepository.getRepositoryRoot(true);
	    
	    out.println(svnRoot.toString());
	    
	    Collection<?> entries = svnRepository.getDir("", -1, null, (Collection<?>)null);
        Iterator<?> iterator =entries.iterator();
        SVNNodeKind  svnNodeKind  = svnRepository.checkPath("", -1);
        if ( svnNodeKind == SVNNodeKind.NONE ) {
        } else if ( svnNodeKind == SVNNodeKind.DIR ) {
        	out.println("is dir");return;
        } else {
        	out.println("is file");
        	SVNProperties fileProperties = new SVNProperties( );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( );
            svnRepository.getFile("Documents", -1, fileProperties , baos);
            try {
				baos.writeTo( System.out );
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        while (iterator.hasNext()) {
            SVNDirEntry entry =(SVNDirEntry) iterator.next();
            System.out.println("/" + entry.getName() + " (author: '" + entry.getAuthor() + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")");
            /*
             * 检查此条目是否为目录，如果为目录递归执行
             */
            /*if (entry.getKind() ==SVNNodeKind.DIR) {
                listEntries(repository,(path.equals("")) ? entry.getName()
                        : path + "/" + entry.getName());
            }*/

        }
	}
}
