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

    // Make second list optional by defaulting to "$Token" (universal match)
    secondList = '$Token';
    if (params.keywords2) {
      secondList = params.keywords2;
    }

    AssociationMatrix matrix = AssociationMatrix.build(params.keywords1, secondList, params.searchArea);

    WebUtils webUtils = new WebUtils(grailsApplication.config.mimir.username, grailsApplication.config.mimir.password);

    matrix.populate(grailsApplication.config.mimir.indexURL, webUtils);

    render(view: "results", model: [matrix: matrix as JSON])
  }

  def sentences() {

    Shrapnel query = new Shrapnel("({Sentence} OVER (\"" + params.source +
            "\" AND \"" + params.target + "\"))", new ArrayList<String>());

    WebUtils webUtils = new WebUtils(grailsApplication.config.mimir.username, grailsApplication.config.mimir.password);
    query.execute(grailsApplication.config.mimir.indexURL, webUtils, true);

    render (view: "sentences", model: [results: query.getResults()]);

  }
}
