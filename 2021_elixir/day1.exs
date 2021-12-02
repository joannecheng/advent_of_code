defmodule Day1 do
  def test do
    IO.puts("Hello")
  end
end

numbers = [199, 200, 208, 210, 200, 207, 240, 269, 260, 263]
IO.inspect(numbers)
IO.inspect(Enum.chunk_every(numbers, 2, 1, :discard))
Day1.test()

List.foldl(
  Enum.chunk_every(numbers, 2, 1, :discard),
  0,
  fn x, acc ->
    if Enum.at(x, 1) > Enum.at(x, 0) do
      acc + 1
    else
      acc
    end
  end
)
