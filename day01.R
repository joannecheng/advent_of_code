nums <- c( 1721, 979, 366, 299, 675, 1456 )

test1 <- function (x, y) { x + y }

for(i in seq_along(nums)) {
  lapply(nums[0:-i], FUN = purrr::partial(test1, nums[i]))
  #for (y in  nums[0:-1]) {
  #  if ((x + y) == 2020) {
  #    print(x * y)
  #  }
  #} 
}

nums[0:-1]
nums[1]
for(x in nums) {
  for (y in  nums[0:-1]) {
    for (z in nums[0:-2]) {
      if ((x + y + z) == 2020) {
        print(x * y * z)
      }
    }
  } 
}

test1 <- function(x,y ) { x + y }
lapply(nums, nums[0:-1], FUN=test1)

