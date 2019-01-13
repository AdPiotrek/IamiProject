# SwingGallery

## Error handling

Error handler is overwritten with new implementation, i didn't make server but i
 implemented some mock observable which should send logs to server ( request is not send not to throw error ).
 Http requests ( flickr type of errors ) are handled in interceptor, which shows messages via toastr. 
 
## Loading indicators

Loading indicator is handled from component level, but it is also possible to manage that via interceptor and communication
services and one global component instance.

