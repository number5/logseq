(ns logseq.publishing
  "This node only ns provides api fns for exporting a publishing app"
  (:require [logseq.publishing.html :as publish-html]
            [logseq.publishing.export :as publish-export]))

(defn export
  "Exports the given graph-dir and db to the specific output-dir. Most of the graph
configuration is done through logseq/config.edn. There are a few explicit options that
can be passed:
* :ui/theme - Theme mode that can either be 'light' or 'dark'.
* :html-options - A map of values that are inserted into index.html. Map keys
  can be icon, name, alias, title, description and url"
  [db static-dir graph-dir output-dir options]
  (let [options' (cond-> options
                         (:ui/theme options)
                         (assoc :app-state {:ui/theme (:ui/theme options)}))
        {:keys [html asset-filenames]} (publish-html/build-html db options')]
    (publish-export/create-export html static-dir graph-dir output-dir {:asset-filenames asset-filenames})))