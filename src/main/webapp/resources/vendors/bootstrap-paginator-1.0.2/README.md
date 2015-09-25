# Bootstrap Paginator v1.0

---

Bootstrap Paginator is a jQuery plugin that simplifies the rendering of Bootstrap Pagination component. It provides methods to automates the update of the pagination status and also some events to notify the status changes within the component. For documentation and examples, please visit [Bootstrap Paginator Website](http://bootstrappaginator.org/ "Click to visit Bootstrap Paginator").

# Changes

v 1.0

* Add the support for bootstrap v3.
* Make the page change happened in page click event stoppable
* Remove the visibility control within getPages function and leave it with shouldShowPage function.

v 0.6

* Fix the bug that will cause page out of range when updating the current page together with the total pages.

v 0.5.1

* Use html entities in default text function instead of plain text to fix the display problem in IE 7-9

# Copyright and License
Copyright 2013 Yun Lai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.