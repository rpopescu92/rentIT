var gulp = require('gulp');
var inject = require('gulp-inject'),
    bowerFiles = require('main-bower-files'),
    angularFileSort = require('gulp-angular-filesort');
gulp.task('inject', function () {
    var target = gulp.src('index.html');
    var sources = gulp.src(bowerFiles(), {read: false});

    return target.pipe(inject(sources))
                 .pipe(inject(gulp.src('static/**/**/*.js')))
         .pipe(gulp.dest('.'));
 });