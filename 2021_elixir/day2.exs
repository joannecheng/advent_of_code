defmodule Day2 do
  def parse_input(path) do
    path
    |> File.read!()
    |> String.split("\n", trim: true)
    |> Enum.map(&String.split(&1, " "))
  end

  def move(row, movements) do
    steps = String.to_integer(Enum.at(row, 1))

    case(Enum.at(row, 0)) do
      "forward" -> Map.update(movements, :h, 0, &(&1 + steps))
      "down" -> Map.update(movements, :v, 0, &(&1 + steps))
      "up" -> Map.update(movements, :v, 0, &(&1 - steps))
    end
  end

  def part1(input) do
    init = %{h: 0, v: 0}

    result_movement = Enum.reduce(input, init, fn x, acc -> move(x, acc) end)
    Map.get(result_movement, :h) * Map.get(result_movement, :v)
  end
end

_test_input = [
  ["forward", 5],
  ["down", 5],
  ["forward", 8],
  ["up", 3],
  ["down", 8],
  ["forward", 2]
]

input = Day2.parse_input("../resources/day02.txt")
IO.inspect(Day2.part1(input))
