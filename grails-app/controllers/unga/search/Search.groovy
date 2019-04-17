package unga.search;

import grails.converters.JSON

import java.net.URL;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Collections;

import java.io.IOException;

import gate.mimir.tool.WebUtils;

import gate.mimir.search.RemoteQueryRunner;

import tnt.*;

class SearchController {

  def index() {
    redirect(uri:'/')
  }

  def results() {

    AssociationMatrix matrix = AssociationMatrix.build(params.keywords1, params.keywords2, params.from, params.to);

    WebUtils webUtils = new WebUtils(grailsApplication.config.mimir.username, grailsApplication.config.mimir.password);

    matrix.populate(grailsApplication.config.mimir.indexURL, webUtils);

    //TODO build the results in a way D3 can display then return the JSON

    System.out.println(AssociationMatrix.convertDate(params.from));

    render(view: "results", model: [matrix: matrix as JSON, from: AssociationMatrix.convertDate(params.from), to: AssociationMatrix.convertDate(params.to)])
  }

  def sentences() {

    Shrapnel query = new Shrapnel("({Sentence} OVER (\"" + params.source +
            "\" AND \"" + params.target + "\"))", new ArrayList<String>());

    WebUtils webUtils = new WebUtils(grailsApplication.config.mimir.username, grailsApplication.config.mimir.password);
    query.execute(grailsApplication.config.mimir.indexURL, webUtils, true);

    render (view: "sentences", model: [results: query.getResults()]);

  }
}
