/**
 * Created by ejacob on 3/30/2016.
 */
require (['jquery', 'wulf/tree'], function ($) {
    $ (document).ready (function () {

        // ******* for pure HTML/CSS/JS components *********//
        var pureHtmlCssJsChildNodesArray = [

            {
                "name": "Layout",
                "type": "item",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "demo-refer-ot.html",
                    "id": "Layout",
                    "show": "true"
                }
            },

            {
                "name": "Colors",
                "type": "item",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "demo-dev-colors.html",
                    "id": "Color",
                    "show": "true"
                }
            },

            {
                "name": "Typography",
                "type": "item",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "components/typography.html",
                    "id": "Typography",
                    "show": "true"
                }
            },

            {
                "name": "Components",
                "type": "folder",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "demo-examples-components.html",
                    "id": "Components",
                    "show": "true"
                }
            },
            {
                "name": "Text field",
                "type": "item",
                "attr": {"parentNode": "Components", "src": "components/inputfield.html"}
            },
            {
                "name": "textarea",
                "text": "Text area",
                "type": "item",
                "attr": {"parentNode": "Components", "src": "components/textarea.html", "show": "true"}
            },
            //{
            //    "name": "Text area with scrollbar",
            //    "type": "item",
            //    "attr": {"parentNode": "textarea", "src": "components/textarea-scrollbar.html"}
            //},
            {
                "name": "Button",
                "type": "folder",
                "attr": {
                    "parentNode": "Components",
                    "src": "components/buttons.html",
                    "id": "Button",
                    "show": "true"
                }
            },
            {
                "name": "Tab buttons",
                "type": "item",
                "attr": {"parentNode": "Button", "src": "components/actionbuttons.html"}
            },
            {
                "name": "Toggle button",
                "type": "item",
                "attr": { "parentNode": "Button", "src": "components/toggle_buttons.html" }
            },
            {
                "name": "Radio button",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/radiobutton.html" }
            },
            {
                "name": "Checkbox",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/checkbox.html" }
            },
            {
                "name": "lists",
                "text": "List",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/lists.html", "show": "true" }
            },
            {
                "name": "list-scroll",
                "text": "List with scrollbar",
                "type": "item",
                "attr": { "parentNode": "lists", "src": "components/lists-scrollbar.html" }
            },
            {
                "name": "description-list",
                "text": "Description list (dl/dt/dd)",
                "type": "item",
                "attr": { "parentNode": "lists", "src": "components/list-description.html" }
            },
            {
                "name": "multicolumn-list",
                "text": "Multicolumn list",
                "type": "item",
                "attr": { "parentNode": "lists", "src": "components/list-multicolumn.html" }
            },
            {
                "name": "pulldown",
                "text": "Pull down",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/dropdowns.html", "show": "true" }
            },
            {
                "name": "pulldown-scroll",
                "text": "Pull down with scrollbar",
                "type": "item",
                "attr": { "parentNode": "pulldown", "src": "components/dropdowns-scrollbar.html" }
            },
            {
                "name": "Drilldown",
                "type": "folder",
                "attr": {
                    "parentNode": "Components",
                    "src": "components/drilldown.html",
                    "id": "Drilldown",
                    "show": "true"
                }
            },
            {
                "name": "Drilldown without arrow",
                "type": "item",
                "attr": { "parentNode": "Drilldown", "src": "components/drilldown-without-arrow.html" }
            },
            {
                "name": "Combo box",
                "text": "Combo box",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/combobox.html", "show": "true" }
            },
            {
                "name": "Combobox-scroll",
                "text": "Combo box with scrollbar",
                "type": "item",
                "attr": { "parentNode": "Combo box", "src": "components/combobox-scrollbar.html" }
            },
            {
                "name": "Scroll",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/scroll.html" }
            },
            {
                "name": "Spinner",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/spinner.html" }
            },
            {
                "name": "Slider",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/slider.html" }
            },
            {
                "name": "Link", "type": "item",
                "attr": { "parentNode": "Components", "src": "components/links.html" }
            },
            {
                "name": "Alert",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/alerts.html" }
            },
            {
                "name": "Balloon",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/popover.html", "show": "true" }
            },
            {
                "name": "Balloon (New)",
                "type": "item",
                "attr": { "parentNode": "Balloon", "src": "components/balloon.html" }
            },
            {
                "name": "Tooltip",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/tooltip.html" }
            },
            {
                "name": "Progress",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/progress.html" }
            },
            {
                "name": "Panel",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/panels.html", "show": "true" }
            },
            {
                "name": "Panel-scroll",
                "text": "Panel with scrollbar",
                "type": "item",
                "attr": { "parentNode": "Panel", "src": "components/panels-scrollbar.html" }
            },
            {
                "name": "Panel-vertical",
                "text": "Panel slide vertical",
                "type": "item",
                "attr": { "parentNode": "Panel", "src": "components/panels-slide-vertical.html" }
            },
            {
                "name": "Panel-horizontal",
                "text": "Panel slide horizontal",
                "type": "item",
                "attr": { "parentNode": "Panel", "src": "components/panels-slide-horizontal.html" }
            },
            {
                "name": "tabbedPane",
                "text": "Tabbed pane",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/navs.html", "show": "true" }
            },
            {
                "name": "tabbedPane-scroll",
                "text": "Tabbed pane with scrollbar",
                "type": "item",
                "attr": { "parentNode": "tabbedPane", "src": "components/navs-scrollbar.html" }
            },
            {
                "name": "Table",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/tables.html", "show": "true" }
            },
            {
                "name": "Table with scrollbar <em class='draft'>(D)</em>",
                "type": "item",
                "attr": { "parentNode": "Table", "src": "components/tables-scrollbar.html" }
            },
            {
                "name": "Table with filters",
                "type": "item",
                "attr": { "parentNode": "Table", "src": "components/tables-filter.html" }
            },
            {
                "name": "Table with paging",
                "type": "item",
                "attr": { "parentNode": "Table", "src": "components/tables-page.html" }
            },
            {
                "name": "Table with change indicator",
                "type": "item",
                "attr": { "parentNode": "Table", "src": "components/tables-indicator.html" }
            },
            {
                "name": "Table with errors",
                "type": "item",
                "attr": { "parentNode": "Table", "src": "components/tables-error.html" }
            },
            {
                "name": "table-jqwidget",
                "text": "Table (jqWidget)",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/grid.html", "show": "true" }
            },
            {
                "name": "Table with filters <em class='draft'>(D)</em>",
                "type": "item",
                "attr": { "parentNode": "table-jqwidget", "src": "components/grid-filter.html" }
            },
            {
                "name": "Table with sorter",
                "type": "item",
                "attr": { "parentNode": "table-jqwidget", "src": "components/grid-sorter.html" }
            },
            {
                "name": "Table with paging",
                "type": "item",
                "attr": { "parentNode": "table-jqwidget", "src": "components/grid-paging.html" }
            },
            {
                "name": "Table with indicator",
                "type": "item",
                "attr": { "parentNode": "table-jqwidget", "src": "components/grid-indicator.html" }
            },
            {
                "name": "Table with error",
                "type": "item",
                "attr": { "parentNode": "table-jqwidget", "src": "components/grid-error.html" }
            },
            {
                "name": "Table with add/delete row",
                "type": "item",
                "attr": { "parentNode": "table-jqwidget", "src": "components/grid-add-delete-rows.html" }
            },
            {
                "name": "Icon",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "components/icons.html" }
            },
            {
                "name": "Icons in table cells",
                "type": "item",
                "attr": { "parentNode": "Icon", "src": "components/icons-table-cells.html" }
            },
            {
                "name": "Breadcrumb",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/breadcrumb.html" }
            },
            {
                "name": "Search",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "components/search.html" }
            },
            {
                "name": "Widgets",
                "type": "folder",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "demo-examples-widgets.html",
                    "id": "Widgets",
                    "show": "true"
                }
            },
            { "name": "Form", "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/form.html" }
            },
            {
                "name": "Banner",
                "type": "folder",
                "attr": {
                    "parentNode": "Widgets",
                    "src": "components/example-banner.html",
                    "id": "Banner",
                    "show": "true"
                }
            },
            {
                "name": "Action Navigation Control",
                "type": "item",
                "attr": {
                    "parentNode": "Banner",
                    "src": "components/example-banner-selfcare.html"
                }
            },
            {
                "name": "Nokia Banner",
                "type": "item",
                "attr": {
                    "parentNode": "Banner",
                    "src": "components/example-banner-normal.html"
                }
            },
            {
                "name": "Nokia Banner (overflow)",
                "type": "item",
                "attr": {
                    "parentNode": "Banner",
                    "src": "components/example-banner-overflow.html"
                }
            },
            {
                "name": "Nokia Banner (condensed)",
                "type": "item",
                "attr": {
                    "parentNode": "Banner",
                    "src": "components/example-banner-condensed.html"
                }
            },
            {
                "name": "Nokia Banner (filter bar)",
                "type": "item",
                "attr": {
                    "parentNode": "Banner",
                    "src": "components/example-banner-filter-bar.html"
                }
            },
            {
                "name": "About dialog",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/example-about.html" }
            },
            {
                "name": "Login",
                "type": "folder",
                "attr": {
                    "parentNode": "Widgets",
                    "src": "components/example-login-intro.html",
                    "id": "Login",
                    "show": "true"
                }
            },
            {
                "name": "Standard",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "components/example-login.html" }
            },
            {
                "name": "Account sign-up",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "components/example-login-signup.html" }
            },
            {
                "name": "Product",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "components/example-login-product.html" }
            },
            {
                "name": "Legal copy",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "components/example-login-legalcopy.html" }
            },
            {
                "name": "Legal acceptance",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "components/example-login-acceptance.html" }
            },
            {
                "name": "Error",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "components/example-login-error.html" }
            },
            {
                "name": "With four column",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "components/example-login-four-column.html" }
            },
            {
                "name": "File upload",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/file-upload.html" }
            },
            {
                "name": "Dialog",
                "type": "folder",
                "attr": {
                    "parentNode": "Widgets",
                    "src": "components/example-dlg-parent.html",
                    "id": "Dialog",
                    "show": "true"
                }
            },
            {
                "name": "Plain subheader",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-subheader.html" }
            },
            {
                "name": "Plain subheader instruction",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-subheader-instruct.html" }
            },
            {
                "name": "Plain instruction",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-instruction.html" }
            },
            {
                "name": "Active Dialog",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-active.html" }
            },
            {
                "name": "Error",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-error.html" }
            },
            {
                "name": "Warning",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-warning.html" }
            },
            {
                "name": "Confirmation",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-confirmation.html" }
            },
            {
                "name": "Resize and Drag <em class='draft'>(D) </em>",
                "type": "item",
                "attr": { "parentNode": "Dialog", "src": "components/example-dlg-subheader-resize.html" }
            },
            {
                "name": "Charts",
                "type": "folder",
                "attr": { "parentNode": "Widgets", "src": "components/example-chart.html", "id": "Chart" }
            },
            {
                "name": "Bar (jqWidget)",
                "type": "item",
                "attr": { "parentNode": "Charts", "src": "components/example-chart-jqxcharts-bar.html" }
            },
            {
                "name": "Line (jqWidget)",
                "type": "item",
                "attr": { "parentNode": "Charts", "src": "components/example-chart-jqxcharts-line.html" }
            },
            {
                "name": "Speedometer (jqWidget) <em class='draft'>(D)</em>",
                "type": "item",
                "attr": { "parentNode": "Charts", "src": "components/example-chart-jqxgauge.html" }
            },
            {
                "name": "Pie (jqWidget)",
                "type": "item",
                "attr": { "parentNode": "Charts", "src": "components/example-chart-jqxcharts-pie.html" }
            },
            {
                "name": "Bar (HighCharts)",
                "type": "item",
                "attr": { "parentNode": "Charts", "src": "components/example-chart-highcharts-bar.html" }
            },
            /*
             {
             "name": "Bar - horizontal with OT3",
             "type": "item",
             "attr": {
             "parentNode": "Chart",
             "src": "components/example-chart-highcharts-bar.html"
             }
             },*/
            {
                "name": "Validations",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/example-validation.html" }
            },
            {
                "name": "Calendar",
                "type": "folder",
                "attr": { "parentNode": "Widgets", "src": "components/example-calendar.html" }
            },
            {
                "name": "Calendar with time",
                "type": "item",
                "attr": { "parentNode": "Calendar", "src": "components/example-calendar-timer.html" }
            },
            {
                "name": "TimeZone",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/example-timezone.html" }
            },
            {
                "name": "Lifecycle",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/lifecycle.html" }
            },
            {
                "name": "Flyout menu",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/flyoutmenu.html" }
            },
            {
                "name": "Flyout Activity area",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/flyoutactivatearea.html" }
            },
            {
                "name": "Taskpad",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/taskpad.html" }
            },
            {
                "name": "Drawer",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/drawer.html" }
            },
            {
                "name": "Tree",
                "type": "folder",
                "attr": { "parentNode": "Widgets", "src": "components/example-tree.html", "show": "true" }
            },
            {
                "name": "Tree-scroll",
                "text": "Tree with scrollbar",
                "type": "item",
                "attr": { "parentNode": "Tree", "src": "components/example-tree-scrollbar.html" }
            },
            {
                "name": "Tree-folder-select",
                "text": "Tree with folder select",
                "type": "item",
                "attr": { "parentNode": "Tree", "src": "components/example-tree-folder-select.html" }
            },
            {
                "name": "Tree-html-css",
                "text": "Tree (HTML/CSS)",
                "type": "item",
                "attr": { "parentNode": "Tree", "src": "components/example-tree-html-css.html" }
            },
            {
                "name": "Tree table",
                "text": "Tree table",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/example-treegrid.html" }
            },
            {
                "name": "Dialog wizard",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/example-dlg-wizard.html" }
            },
            {
                "name": "Product footer",
                "type": "folder",
                "attr": {
                    "parentNode": "Widgets",
                    "src": "components/footer-product.html",
                    "id": "nokia_footer_product",
                    "show": "true"
                }
            },
            {
                "name": "Footer with copyright",
                "type": "item",
                "attr": { "parentNode": "Product footer", "src": "components/footer_copyright_basic.html" }
            },
            {
                "name": "Footer bordered with links",
                "type": "item",
                "attr": { "parentNode": "Product footer", "src": "components/footer_links_bordered.html" }
            },
            {
                "name": "Consumer footer",
                "type": "item",
                "attr": { "parentNode": "Product footer", "src": "components/footer-consumer.html" }
            },
            {
                "name": "Local navigation",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/local_navigation.html" }
            },
            {
                "name": "Secondary navigation vertical",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/navigation_vertical.html" }
            },
            {
                "name": "Secondary navigation horizontal",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/navigation_horizontal.html" }
            },
            {
                "name": "Localized sub navigation",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "components/localized-sub-navigation.html" }
            },

            {
                "name": "Visualization (chart, table)",
                "type": "folder",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "demo-refer-ot.html",
                    "id": "Visualization",
                    "show": "true"
                }
            },
            {
               "name": "Visualization table cell",
               "text": "Visualization table cell<em class='draft'>(D)</em>",
               "type": "item",
               "attr": {"parentNode": "Visualization (chart, table)", "src": "components/visualization-table-cell.html", "show": "true"}
            }
        ];

        //********* for Angular 1.5 tree nodes ***********//
        var angular15childNodesArray = [
            {
                "name": "Components",
                "type": "folder",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "demo-examples-components.html",
                    "id": "Components",
                    "show": "true"
                }
            },
            {
                "name": "Alert",
                "text": "Alert",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/alerts.html" }
            },
            {
                "name": "combobox",
                "text": "Combobox",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/combobox.html", "show": "true" }
            },
            {
                "name": "tabbedPane",
                "text": "Tabbed pane",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/tabs.html", "show": "true" }
            },
            {
                "name": "panels",
                "text": "Panels",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/panels.html", "show": "true" }
            },
            {
                "name": "text field",
                "text": "Text field",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/inputfield.html", "show": "true" }
            },
            {
                "name": "form",
                "text": "Form",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/form.html", "show": "true" }
            },
            {
                "name": "Textarea",
                "text": "Text area",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/textarea.html", "show": "true" }
            },
            {
                "name": "PullDown",
                "text": "Pull down",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/dropdowns.html", "show": "true" }
            },
            {
                "name": "progress",
                "text": "Progress",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/progress.html", "show": "true" }
            },

            {
                "name": "search",
                "text": "Search",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/search.html", "show": "true" }
            },

            {
                "name": "Toggle Button",
                "text": "Toggle Button",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/toggle_button.html", "show": "true" }
            },
            {
                "name": "list",
                "text": "List",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/list.html", "show": "true" }
            },

            {
                "name": "table",
                "text": "Table<em class='draft'>(D)</em>",
                "type": "folder",
                "attr": { "parentNode": "Components", "src": "ng-components/tables-smart-table.html", "show": "true" }
            },
            {
                "name": "Table with filters",
                "type": "item",
                "attr": { "parentNode": "table", "src": "ng-components/tables-smart-table-filters.html" }
            },
            {
                "name": "Table with paging",
                "type": "item",
                "attr": { "parentNode": "table", "src": "ng-components/tables-smart-table-pagination.html" }
            },
            {
                "name": "Table with change indicator",
                "type": "item",
                "attr": { "parentNode": "table", "src": "ng-components/tables-smart-table-indicator.html" }
            },
            {
                "name": "Table with errors",
                "type": "item",
                "attr": { "parentNode": "table", "src": "ng-components/tables-smart-table-errors.html" }
            },
            {
                "name": "Tab buttons",
                "text": "Tab buttons",
                "type": "item",
                "attr": {"parentNode": "Components", "src": "ng-components/actionbuttons.html"}
            },
            {
                "name": "tooltip",
                "text": "Tooltip<em class='draft'>(D)</em>",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/tooltip.html", "show": "true" }

            },
            {
                "name": "Spinner",
                "text": "Spinner",
                "type": "item",
                "attr": { "parentNode": "Components", "src": "ng-components/spinner.html" }
            },
            {
                "name": "Widgets",
                "type": "folder",
                "attr": {
                    "parentNode": "rootNode",
                    "src": "demo-examples-widgets.html",
                    "id": "Widgets",
                    "show": "true"
                }
            },
            {
                "name": "Login",
                "type": "folder",
                "attr": {
                    "parentNode": "Widgets",
                    "src": "ng-components/login.html",
                    "id": "Login",
                    "show": "true"
                }
            },
            {
                "name": "Standard",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "ng-components/login-standard.html" }
            },
            {
                "name": "Account sign-up",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "ng-components/login-signup.html" }
            },
            {
                "name": "Product",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "ng-components/login-product.html" }
            },
            {
                "name": "Legal copy",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "ng-components/login-legalcopy.html" }
            },
            {
                "name": "Legal acceptance",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "ng-components/login-acceptance.html" }
            },
            {
                "name": "Error",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "ng-components/login-error.html" }
            },
            {
                "name": "With four column",
                "type": "item",
                "attr": { "parentNode": "Login", "src": "ng-components/login-fourcolumn.html" }
            },
            {
                "name": "dialog",
                "text": "Dialog",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "ng-components/dialog.html", "show": "true" }
            },
            {
                "name": "inlineInfo",
                "text": "Inline info",
                "type": "item",
                "attr": {"parentNode": "Widgets", "src": "ng-components/inlineinfo.html", "show": "true"}
            },
            {
                "name": "Tree",
                "text": "Tree<em class='draft'>(D)</em>",
                "type": "item",
                "attr": { "parentNode": "Widgets", "src": "ng-components/tree.html", "id": "Tree", "show": "true"}
            },
            {
                "name": "Product footer",
                "type": "folder",
                "attr": {
                    "parentNode": "Widgets", "src": "ng-components/footer-product.html", "show": "true"
                }
            },
            {
                "name": "Footer with copyright",
                "type": "item",
                "attr": {
                    "parentNode": "Product footer", "src": "ng-components/footer_copyright_basic.html", "show": "true"
                }
            },
            {
                "name": "Footer bordered with links",
                "type": "item",
                "attr": {
                    "parentNode": "Product footer", "src": "ng-components/footer_links_bordered.html", "show": "true"
                }
            },
            {
                "name": "Consumer footer",
                "type": "item",
                "attr": {
                    "parentNode": "Product footer", "src": "ng-components/footer-consumer.html", "show": "true"
                }
            },
            {
                "name": "calendar",
                "text": "Calendar<em class='draft'>(D)</em>",
                "type": "item",
                "attr": {"parentNode": "Widgets", "src": "ng-components/calendar.html", "show": "true"}
            },
            {
                "name": "Dashboard tile",
                "text": "Dashboard tile<em class='draft'>(D)</em>",
                "type": "item",
                "attr": {"parentNode": "Widgets", "src": "ng-components/dashboard-tile.html", "show": "true"}
            },
            {
                "name": "Banner",
                "text": "Banner<em class='draft'>(D)</em>",
                "type": "folder",
                "attr": {
                    "parentNode": "Widgets",
                    "src": "ng-components/banner.html",
                    "id": "Banner",
                    "show": "true"
                }
            },
            {
                "name": "Normal Banner",
                "text": "Normal Banner<em class='draft'>(D)</em>",
                "type": "item",
                "attr": {"parentNode": "Banner", "src": "ng-components/banner-normal.html"}
            }
        ];

        var hash = location.hash;

        var childNodesArray = (hash === '#Angular15') ? angular15childNodesArray : pureHtmlCssJsChildNodesArray;
        var componentsType = (hash === '#Angular15') ? 'Angular 1.5' : 'Pure HTML/CSS/JS';
        $('#component_type_indication').text(componentsType);

        function staticDataSource (openedParentData, callback) {
            if (typeof openedParentData.attr !== 'undefined') {
                callback ({
                    data: get (childNodesArray, openedParentData.name)
                });
            }
            else {
                callback ({
                    data: get (childNodesArray, "rootNode")
                });
            }
        }

        function get (arrPerson, objValue) {
            return $.grep (arrPerson, function (cur, i) {
                return cur.attr.parentNode === objValue;
            });
        }

        var contentTree = $ ('#myTree');
        contentTree.tree ({
            dataSource: staticDataSource,
            folderSelect: true,
            seperator: true
        });

        contentTree.tree ('openFolder', $ ('#Content'));
        contentTree.tree ('openFolder', $ ('#Components'));
        contentTree.tree ('openFolder', $ ('#Widgets'));

        $ (".tree-scroll").nScrollbar();
        //$ (".tree-scroll").nScrollbar({axis:'yx', advanced:{autoExpandHorizontalScroll:true}});
    });
});